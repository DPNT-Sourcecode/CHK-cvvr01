package runner

import tdl.client.queue.ImplementationRunnerConfig
import tdl.client.runner.ChallengeSessionConfig
import runner.CredentialsConfigFile.readFromConfigFile

object Utils {
    @Throws(ConfigNotFoundException::class)
    fun getConfig(): ChallengeSessionConfig {
        return ChallengeSessionConfig.forJourneyId(readFromConfigFile("tdl_journey_id"))
            .withServerHostname(readFromConfigFile("tdl_hostname"))
            .withColours(readFromConfigFile("tdl_use_coloured_output", "true").toBoolean())
            .withRecordingSystemShouldBeOn(readFromConfigFile("tdl_require_rec", "true").toBoolean())
    }

    @Throws(ConfigNotFoundException::class)
    fun getRunnerConfig(): ImplementationRunnerConfig {
        return ImplementationRunnerConfig()
            .setRequestQueueName(readFromConfigFile("tdl_request_queue_name"))
            .setResponseQueueName(readFromConfigFile("tdl_response_queue_name"))
            .setHostname(readFromConfigFile("tdl_hostname"))
    }
}
